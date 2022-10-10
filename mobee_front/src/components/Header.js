/* eslint-disable */

import React from 'react';

// SCSS
import "../styles/header.scss";

// 리덕스
import { useSelector } from 'react-redux';
// 로그아웃
import { actionCreators as userActions } from "../redux/modules/user";
import { useDispatch } from "react-redux";

// 페이지 이동을 위한 history
import { history } from '../redux/configStore';
import { BrowserRouter } from 'react-router-dom';
import { getCookie } from '../shared/cookie';


// 헤더 컴포넌트
const Header = (props) => {
  
  const dispatch = useDispatch();
  // 로그인 여부 가져오기
  const is_login = useSelector(state => state.user.is_login);
  const is_refresh = useSelector(state => state.user.is_refresh);
  
  if(is_login == false){
    is_login == is_refresh;
  }

  const is_admin = false;

  // 토큰
  const username = localStorage.getItem('login_name');
  const accessToken = localStorage.getItem('is_token');

  const adminName = username;
  const admin = 'admin';

  console.log(adminName);
  console.log(`admin ${admin==adminName}`)

  // 로그아웃
  const logOut = () => {
    dispatch(userActions.logoutStorage());
  }

  React.useEffect(() => {
    dispatch(userActions.logInCheckStorage());
  }, [])
  

  return (
    <BrowserRouter>
    <React.Fragment>
      <div className="header">
        <nav className="navbar navbar-expand-md navbar-light">
          <a className="navbar-brand"
            // 클릭시 메인으로 돌아가기
            onClick={() => { history.push('/') }}
          ><span className="logo-bold">Movie</span> <span className="logo">App</span></a>
          <ul className="navbar-nav mr-auto">
            <li className="nav-item">
              <a className="nav-link"
                // 클릭시 메인
                onClick={() => { history.push('/') }}
              ><span className="logo-bold">Mobee</span></a>
            </li>
            <li className='nav-item'>
              {is_login ?
                (<a className='nav-link'
                //클릭시 채팅
                onClick={() => {location.replace("http://34.227.181.214:8080/chat/room?token=" + accessToken)}}
                ><span className='logo-bold'>채팅방</span></a>)
                :
                (<a className='nav-link'
                //클릭시 메인화면
                onClick={() => {alert('로그인을 해주세요!')}}
                ><span className='logo-bold'>채팅방</span></a>)
              }
            </li>
            <li className='nav-item'>
              <a className='nav-link'
                //클릭시 지도
                onClick={() => {history.push('/KakaoMap')}}
                ><span className='logo-bold'>지도보기</span></a>
            </li>
            <li className='nav-item'>
            {`${admin==adminName}` ?
                (<a className='nav-link'
                //클릭시 작동
                onClick={() => {location.replace("http://34.227.181.214:8080/admin/account")}}
                ><span className='logo-bold'>관리자 페이지</span></a>)
                :
                (<a className='nav-link'
                //클릭시 메인화면
                onClick={() => {alert('관리자만 입장 가능합니다.')}}
                ><span className='logo-bold'>관리자 페이지</span></a>)
              }
            </li>
          </ul>
          <ul className="navbar-nav">


            <li className="nav-item">
              {/* 로그인 상태이면 로그아웃 표시하기 */}
              {is_login ?
                (<a className="nav-link"
                  onClick={logOut}
                >로그아웃</a>)
                :
                (<a className="nav-link"
                  onClick={() => {
                    { history.push('/login') }
                  }}
                >로그인</a>)
              }

            </li>
          </ul>

        </nav>
      </div>
      <hr className="headerLine"></hr>
    </React.Fragment>
    </BrowserRouter>
  )
};

export default Header;
