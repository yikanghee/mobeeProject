/* eslint-disable */

import React from 'react';

// 라우터
import { withRouter } from 'react-router';
import { Route, Link } from 'react-router-dom';

// SCSS
import '../styles/description.scss';

// 최소 단위 컴포넌트
import { ElSpinner } from '../elements';

// 리덕스 접근
import { useSelector } from 'react-redux';



// 영화의 상세 설명을 보여주는 컴포넌트
const Description = (props) => {
  // 영화 정보 가져오기
  const { description } = useSelector(state => state.movies.movie_info);
  // 가져올 때까지 로딩
  if (!description) {
    return <ElSpinner />
  }
  // 영화 소개
  return (  
    <div className="description">
      <h2 className="introduce">영화 소개</h2>
      <p className="detail">{description}</p>
    </div>
  )
};

export default Description;
