// 영화 정보 모듈
import { createAction, handleActions } from "redux-actions";
// 불변성 관리 패키지
import { produce } from "immer";

// axios
import axios from 'axios';

// 리뷰 액션
import { actionCreators as reviewActions } from "./review";
// 좋아요 액션
import { actionCreators as heartActions } from './heart';


// Actions
// 보여줄 영화 리스트들 다룰 액션
const SET_MOVIE = "SET_MOVIE";
// 현재 페이지, 시작 또는 끝 페이지를 다루는 액션
const UPDATE_CURRENT = "UPDATE_CURRENT";
const UPDATE_START_END = "UPDATE_START_END";
// 상세 페이지 영화 정보를 다룰 액션
const GET_MOVIE_INFO = "GET_MOVIE_INFO";


// Action Creators
const setMovies = createAction(SET_MOVIE, (movie_list) => ({ movie_list }));
const updateCurrent = createAction(UPDATE_CURRENT, (paging) => ({ paging }));
const updateStartEnd = createAction(UPDATE_START_END, (paging) => ({ paging }));
const getMovieInfo = createAction(GET_MOVIE_INFO, (movie_info) => ({ movie_info }));

// Intial State
const initialState = {
  // 영화 목록을 담는 배열
  movie_list: [],
  // 페이지네이션 초기값
  paging: {
    // 시작
    start: 1,
    // 끝
    end: 10,
    // 현재 페이지
    current: 1,
  },
  // 디테일 페이지의 영화 정보
  movie_info: {
     id: 'id',
     imgUrl: 'imgUrl',
     title: 'title',
     description: 'description',
    // createdAt: 'createdAt',
    // modifiedAt: 'modifiedAt',
  }
}

// 페이지 이동
// 현재 z페이지
const updateCurrentPage = (page) => {
  return function (dispatch, getState, { history }) {
    // 리덕스에 현재 페이지 저장
    dispatch(updateCurrent({
      current: page
    }));
    // 서버로부터 영화 리스트 가져오기
    dispatch(movieListAPI());

  }
};

// 처음, 끝 페이지  
const updateStartEndPage = (start, end) => {
  return function (dispatch, getState, { history }) {
    dispatch(updateStartEnd({
      start: start,
      end: end,
    }))
  }
}

// 페이지에 맞춰 영화 리스트 가져오기
const movieListAPI = (select) => {
  return function (dispatch, getState, { history }) {
    let current = getState().movies.paging.current;
    // // select가 좋아요, 별점순인 경우
    // if (select === "heart" || select === "starRate") {
    //   current = 1;
    // }
    console.log(getState().movies.paging.current);

    const API = `http://52.79.250.98:8090/api/movies?sort=${select}&page=${current}&size=24`;  
    axios.get(API)
      .then((response) => {
        return response.data
      })
      .then((_movie_list) => {
        // 리덕스에 담기
        dispatch(setMovies(_movie_list));
      }).catch((error) => {
      });

  }
};

// 영화 상세 정보 가져오기
// 상세 페이지가 렌더링 되면 호출되어 좋아요, 리뷰 정보도 연이어 가져오기 그 후 리덕스에 저장
const movieInfoAPI = (movie_id) => {
  return function (dispatch, getState, { history }) {
    const API = `http://52.79.250.98:8090/api/movies/${movie_id}`;
    axios.get(API)
      .then((response) => {
        return response.data
      })
      .then((_movie_id) => {
        // 리덕스에 담기
        dispatch(getMovieInfo(_movie_id));
      })
      .then(
        // 좋아요 정보 가져오기
        dispatch(heartActions.getHeartAPI(movie_id))
      )
      .then(
        // 리뷰 정보 가져오기
        dispatch(reviewActions.getReviewAPI(movie_id))
      )
      .catch((error) => {
        window.alert('영화 정보를 불러오지 못했습니다. 재시도해주세요.')
      });
  }
}

// Reducers
export default handleActions(
  {
    [SET_MOVIE]: (state, action) =>
      produce(state, (draft) => {
        draft.movie_list = action.payload.movie_list;
      }),
    [UPDATE_CURRENT]: (state, action) =>
      produce(state, (draft) => {
        draft.paging.current = action.payload.paging.current;
      }),
    [UPDATE_START_END]: (state, action) =>
      produce(state, (draft) => {
        draft.paging.start = action.payload.paging.start;
        draft.paging.end = action.payload.paging.end;
      }),
    [GET_MOVIE_INFO]: (state, action) =>
      produce(state, (draft) => {
        draft.movie_info = action.payload.movie_info;
      })
  },
  initialState
);


// Action Creators export
const actionCreators = {
  updateCurrentPage,
  updateStartEndPage,
  movieListAPI,
  movieInfoAPI,
};

export { actionCreators };