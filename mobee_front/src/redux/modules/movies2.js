import {createAction, handleActions} from "redux-actions";

import {produce} from "immer";

import axios from 'axios';

const SET_MOVIE2 = "SET_MOVIE2";
const GET_MOVIE_INFO2 = "GET_MOVIE_INFO2";

const setMoives2 = createAction(SET_MOVIE2, (movie_list2) => ({movie_list2}));
const getMovieInfo2 = createAction(GET_MOVIE_INFO2, (movie_info2) => ({movie_info2 }));

const initialState = {

    movie_list2: [],

    movie_info2: {
        id: 'id',
        imgUrl: 'imgUrl',
        title: 'title',
        description: 'description',
        engDescription: 'engDescription',
    }
};

const movieListAPI2 = () => {
    return function (dispatch, getState, {history}) {
        const API = `http://34.227.181.214:8080/api/movies2`;

        axios.get(API)
            .then((response) => {
                return response.data
            })
            .then((_movie_list2) => {
                dispatch(setMoives2(_movie_list2));
            }).catch((error) => {
        });
        }
    };

const movieInfoAPI2 = (movie_id) => {
    return function (dispatch, getState, {history}){
        const API = `http://34.227.181.214:8080/api/movies2/${movie_id}`;
        axios.get(API)
            .then((response) => {
                return response.data
            })
            .then((_movie_id2) => {
                dispatch(getMovieInfo2(_movie_id2))
            })
            .catch((error) => {
                window.alert('영화 정보를 불러오지 못했습니다. 재시도해주세요');
            })
    }
}

export default handleActions(
    {
        [SET_MOVIE2]: (state, action) =>
            produce(state, (draft) => {
                draft.movie_list2 = action.payload.movie_list2
            }),
        [GET_MOVIE_INFO2]: (state, action) =>
            produce(state, (draft) => {
                draft.movie_info2 = action.payload.movie_info2;
            })
    },
    initialState
);

const actionCreators = {
    movieListAPI2,
    movieInfoAPI2,
}

export {actionCreators};



