/* eslint-disable */

// 사용자 정보 관리 모듈
import { createAction, handleActions } from "redux-actions";
import React from 'react';
// 불변성 관리 패키지
import { produce } from "immer";

// axios
import axios from 'axios';
import { getCookie, setCookie } from "../../shared/cookie";
import { CloseOutlined } from "@material-ui/icons";

// Actions
const LOG_OUT = "LOG_OUT";
const SET_USER = 'SET_USER';


// Action Creators
const setUser = createAction(SET_USER, (user) => ({ user }));
const logOut = createAction(LOG_OUT, (user) => ({ user }));


// Initial state
const initialState = {
  username: null,
  is_login: false,
}

// 회원 가입
const signupAPI = (id, email, pwd, pwd_check) => {
  return function (dispatch, getState, { history }) {
    const API = "http://34.227.181.214:8080/api/account/signup";

    axios.post(API,
      {
        "username": id,
        "email" : email,
        "password": pwd,
        "passwordConfirm": pwd_check,
      },
      {
        headers: {
          'Content-Type': 'application/json',
        }
      })
      .then((response) => {
        window.alert('회원가입 되었습니다. 로그인해주세요.')
        history.push('/login')
      })
      .catch((error) => {
        console.log(error);
        window.alert('존재하는 회원 정보입니다.');
      })
  }
}

// 로그인
const loginAPI = (id, pwd) => {
  return function (dispatch, getState, { history }) {

    const API = "http://34.227.181.214:8080/api/account/login";

    axios.post(API,
      {
        "username": id,
        "password": pwd,
      },
      {
        headers: {
          'Content-Type': 'application/json',
        }
      })
      .then((response) => {
        if (response.data.token) {
          // 로컬스토리지 저장
          localStorage.setItem("is_token", response.data.token);
          localStorage.setItem("is_refresh", response.data.refreshtoken);

          console.log(" 토큰 " + response.data.token);
          console.log("리프레시 토큰" + response.data.refreshtoken);

          const accessToken = response.data.token;
          const refreshToken = response.data.refreshtoken;


          console.log(accessToken);
          console.log(refreshToken);

          // 쿠키에 정보 저장 
          setCookie('token', localStorage.getItem("is_token"));

          axios.defaults.headers.common['Authorization'] = `${accessToken}`;
          axios.defaults.headers.common['RefreshToken'] = `${refreshToken}`;
          
          console.log(getCookie);

          localStorage.setItem("login_id", id);
          localStorage.setItem("login_name", id);

          dispatch(setUser({
            username: id,
          }))
          window.alert('로그인되었습니다.');
          history.push('/');
        }
      }).catch((error) => {
        console.log(error);
        window.alert('잘못된 회원 정보입니다.');
      })
  }
}

// 로그아웃
// 로컬스토리지에서 토큰 지우기
const logoutStorage = () => {
  return function (dispatch, getState, { history }) {
    const id = getState().user.username;
    localStorage.removeItem("is_token");
    localStorage.removeItem("login_id");
    localStorage.removeItem("login_name");
    window.alert('로그아웃 되었습니다.');
    // 로그아웃 후 메인페이지로
    history.replace('/');
    // 리덕스 초기화를 위한 새로고침
    window.location.reload();
  }
}

// 로그인 체크
const logInCheckStorage = () => {
  return function (dispatch, getState, { history }) {
    // 토큰 가져오기
    const token = localStorage.getItem('is_token');
    const id = localStorage.getItem('login_id');
    // 토큰이 없으면 재로그인 alert
    // 있다면 유지
    if (!token) {
      return
    } else {
      dispatch(setUser({
        username: id,
      }))
    }
  };
};

// 비밀번호 찾기
const pwAPI = (email) => {
  return function (dispatch, getState, {history}) {

    const API = "http://34.227.181.214:8080/api/account/findPassword";

    axios.post(API,
        {
          "email" : email,
        },
        {
          headers: {
            'Content-Type': 'application/json',
          }
        }).then((response) => {
      window.alert("이메일에 임시 비밀번호를 전송했습니다. 확인해주세요");
      history.push('login');
    })
        .catch((error) => {
          console.log(error);
          window.alert('유효하지 않은 이메일입니다.');
        })
  };
}

// 아이디 찾기
const idAPI = (email) => {
  return function (dispatch, getState, {history}) {

    const API = "http://34.227.181.214:8080/api/account/findId";

    axios.post(API,
        {
          "email" : email,
        },
        {
          headers: {
            'Content-Type': 'application/json',
          }
        }).then((response) => {
      window.alert("이메일에 아이디를 전송했습니다. 확인해주세요");
      history.push('login');
    })
        .catch((error) => {
          console.log(error);
          window.alert('유효하지 않는 이메일입니다.');
        })
  };
}

// Reducers
export default handleActions(
  {
    [SET_USER]: (state, action) => produce(state, (draft) => {
      draft.username = action.payload.user.username;
      draft.is_login = true;
    }),
    [LOG_OUT]: (state, action) =>
      produce(state, (draft) => {
        draft.user = null;
        draft.is_login = false;
      }),
  },
  initialState
);


// Action Creators export
const actionCreators = {
  signupAPI,
  loginAPI,
  pwAPI,
  idAPI,
  logoutStorage,
  logInCheckStorage,
};

export { actionCreators };