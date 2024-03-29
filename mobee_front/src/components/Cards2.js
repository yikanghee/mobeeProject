/* eslint-disable */


import React from 'react';

// SCSS
import "../styles/cards.scss";

// 리덕스 접근
import { useSelector, useDispatch } from 'react-redux';


// 부트스트랩
import { Card } from 'react-bootstrap';

// 최소 단위 컴포넌트
import { ElSpinner } from '../elements';


import {actionCreators as moviesActions2} from '../redux/modules/movies2';


// 카드 컴포넌트
const Cards2 = (props) => {

  const dispatch = useDispatch();
  const { history } = props;

  React.useEffect(() => {
    dispatch(moviesActions2.movieListAPI2())
  }, []);

  // 영화 목록 가져오기
  const movie_list = useSelector((state) => state.movies2.movie_list2);

  console.log("#######")
  console.log(movie_list)

  // 로딩이 안되었을 시 대기
  if (!movie_list) {
    return (
      <ElSpinner />
    )
  } else {
    // map으로 서버로부터 받아온 영화 정보 하나씩 넣기
    // 정렬은 felx-wrap 으로 적용
    return (
      <div className="cards">
        {movie_list.map((info, idx) => {
          return (
            <Card
              style={{
                width: '150px',
                border: 'none',
                margin: '80px 0px 0px 0px',
                cursor: 'pointer',
              }}
              key={info.id}
            >
              <Card.Img
                style={{
                  boxShadow: "rgba(0, 0, 0, 0.02) 0px 1px 3px 0px, rgba(27, 31, 35, 0.15) 0px 0px 0px 1px"
                }}
                onClick={() => {
                  {
                    history.push(`/detail2/${info.id}`)
                  }
                }}
                variant="top"
                src={info.imgUrl}
                width="160px" />
              <span>{info.title}</span>
            </Card>
          )
        })}
      </div>
    )

  }

};

export default Cards2;
