/* eslint-disable */

import React from 'react';

// 라우터
import { withRouter } from 'react-router';
import { Route, Link } from 'react-router-dom';

// 부트스트랩
import Pagination from 'react-bootstrap/Pagination';

// 모듈
import { useDispatch, useSelector } from "react-redux";
import { actionCreators as moviesActions } from "../redux/modules/movies";


// SCSS
import '../styles/paging.scss';


const Paging = (props) => {
  const dispatch = useDispatch();

  // 리덕스의 페이징 정보 가져오기
  const { start, end, current } = useSelector((state) => state.movies.paging);

  // 총 페이지 수 가져오기
  const totalPages = useSelector((state) => state.movies.movie_list.totalPages);

  // 현재 페이지 영화 목록 가져오기
  React.useEffect(() => {
    // 총 페이지가 10 이하이면 end에 적용하기
    if (totalPages <= 10) {
      dispatch(moviesActions.updateStartEndPage(start, totalPages));
    }
  }, [])

  // 페이지 이동
  const pageMove = (number) => {
    dispatch(moviesActions.updateCurrentPage(number));
  }

  // 인덱스 이동 (이전)
  const indexMoveDown = () => {
    // 10 이하이면 그대로 두기
    if (current <= 10) {

      return
    } else {
      // 10 페이지 전으로
      dispatch(moviesActions.updateStartEndPage(start - 10, end - 10));
      // 현재 페이지도 변경
      dispatch(moviesActions.updateCurrentPage(end - 10));
    }
  }

  // 인덱스 이동 (이후)
  const indexMoveUp = () => {
    // 10 페이지 후로
    dispatch(moviesActions.updateStartEndPage(start + 10, end + 10));
    // 현재 페이지도 변경
    dispatch(moviesActions.updateCurrentPage(start + 10));
  }

  // 페이징 넘버들을 넣을 배열
  let items = [];
  // 페이징 넘버 생성
  for (let number = start; number <= end; number++) {
    // 총페이지를 넘으면 생성 중단
    if (number > totalPages) {
      break
    }
    items.push(
      <Pagination.Item
        key={number}
        active={number === current}
        onClick={() => {
          pageMove(number)
        }}
      >
        {number}
      </Pagination.Item>,
    );
  };
  return (
    <div className="paging">
      <Pagination>
        <Pagination.First
          // 처음 페이지로 가기
          onClick={() => {
            pageMove(1);
            // 시작, 끝 페이지도 이동
            dispatch(moviesActions.updateStartEndPage(1, 10));
          }}
        />
        {/* end가 10 이하이면 나타내지 않기 */}
        {end <= 10 ? (null) : (
          <Pagination.Prev
            // 이전 인덱스 이동
            onClick={() => {
              indexMoveDown()
            }}
          />
        )}
        {items}
        {/* 총 페이지 수가 10이하이면 렌더링하지 않기 */}
        {totalPages <= 10 ? (null) : (
          <Pagination.Next
            // 이후 인덱스 이동
            onClick={() => {
              indexMoveUp()
            }}
          />
        )}
        <Pagination.Last
          // 마지막 페이지로 가기
          onClick={() => {
            pageMove(totalPages)
            dispatch(moviesActions.updateStartEndPage(parseInt(totalPages / 10) + 1, totalPages));
          }}
        />
      </Pagination>
    </div>
  );
};

export default Paging;
