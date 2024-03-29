// 좋아요 정보 관리 모듈
import { createAction, handleActions } from "redux-actions";
// 불변성 관리 패키지
import { produce } from "immer";

// axios
import axios from 'axios';


// Actions
const GET_HEART = "GET_HEART";

// Action Creators
const getHeart = createAction(GET_HEART, (heart) => ({ heart }));


// Initial state
const initialState = {
  heart: {

  }
}

// 좋아요 정보 조회
const getHeartAPI = (movie_id) => {
  return function (dispatch, getState, { history }) {
    const API = `http://34.227.181.214:8080/api/movies/${movie_id}/heart`;
    
    let token = localStorage.getItem('is_token');

    const refreshToken = localStorage.getItem('is_refresh');

    // 로그아웃 상태이면 실행하지 않기
    if (!token) {
      token = refreshToken;
    }

    axios.get(API,
      {
        headers: {
          'Authorization': `${token}`,
        }
      })
      .then((response) => {
        return response.data
      })
      .then((_heart_info) => {
        // 리덕스에 담기
        dispatch(getHeart(_heart_info));
      }).catch((error) => { 
        console.log(error);
      });
  }
}

// 좋아요
const addHeartAPI = (movie_id) => {
  return function (dispatch, getState, { history }) {

    const API = `http://34.227.181.214:8080/api/movies/${movie_id}/heart`;
    let token = localStorage.getItem('is_token');

    const refreshToken = localStorage.getItem('is_refresh');

    // 로그아웃 상태이면 실행하지 않기
    if (!token) {
      token = refreshToken;
    }

    axios({
      method: "POST",
      url: API,
      headers: {
        'Authorization': `${token}`,
        'Content-Type': 'application/json'
      }
    }).then((res) => {
      dispatch(getHeartAPI(movie_id));
      window.alert('좋아요 하셨습니다.');
    }).catch(error => {
      console.log(error);
    });
  }

}



// 좋아요 취소
const deleteHeartAPI = (movie_id) => {
  return function (dispatch, getState, { history }) {

    const API = `http://34.227.181.214:8080/api/movies/${movie_id}/heart`;
    let token = localStorage.getItem('is_token');

    const refreshToken = localStorage.getItem('is_refresh');

    // 로그아웃 상태이면 실행하지 않기
    if (!token) {
      token = refreshToken;
    }

    axios.delete(API,
      {
        headers: {
          'Authorization': `${token}`,
        }
      })
      .then((response) => {
        dispatch(getHeartAPI(movie_id));
        window.alert('좋아요를 취소하셨습니다.');
      })
      .catch((error) => {
        console.log(error);
      });
  }
}


// Reducers
export default handleActions(
  {
    [GET_HEART]: (state, action) => produce(state, (draft) => {
      draft.heart = action.payload.heart;
    }),
  },
  initialState
);


// Action Creators export
const actionCreators = {
  getHeartAPI,
  addHeartAPI,
  deleteHeartAPI,
};

export { actionCreators };