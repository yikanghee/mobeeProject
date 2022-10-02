/* eslint-disable */

import React from 'react';

// scss
import '../styles/detail.scss'

// 컴포넌트
import Header from "./Header";
import Footer from "./Footer";
// 리덕스 접근
import { useSelector, useDispatch } from 'react-redux';
import { actionCreators as movieCreator } from "../redux/modules/movies2";
import MovieInfo2 from "./MovieInfo2";
import Description2 from "./Description2";


// 영화의 정보, 별점,리뷰를 입력할 수 있는 페이지
const Detail2 = (props) => {
  // 해당 페이지의 영화 id 가져오기
  const id = props.match.params.id;

    console.log(id);

  const dispatch = useDispatch();

  React.useEffect(() => {
    // 서버로부터 영화 정보 가져오기
    dispatch(movieCreator.movieInfoAPI2(id));
  }, []);

  return (
    <div className="detail">
      <Header></Header>
      <MovieInfo2
      ></MovieInfo2>
      <Description2></Description2>
      <hr className="detailLine"></hr>
      <Footer></Footer>
    </div >
  )
};

export default Detail2;
