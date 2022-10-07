/* eslint-disable */


import React from 'react';

// SCSS
import '../styles/movie-info.scss';

// 머터리얼 ui
import FavoriteBorderIcon from '@material-ui/icons/FavoriteBorder';
import FavoriteIcon from '@material-ui/icons/Favorite';
import Button from '@material-ui/core/Button';

// 리덕스 접근
import { useSelector, useDispatch } from 'react-redux';

// 최소 단위 컴포넌트
import { ElSpinner } from '../elements';



// 영화정보 컴포넌트
const MovieInfo2 = (props) => {
    const dispatch = useDispatch();

    const { id, imgUrl, title, description} = useSelector(state => state.movies2.movie_info2);
    // 로딩끝나기전에는 스피너 표시
    if (!id) {
        return <ElSpinner />
    }

        return (
            <div
                // 블러 배경
                className="movieInfo"
                style={{ backgroundImage: `url(${imgUrl})` }}
            >
                <div className="blur">
                    <div className="movieInfo__contents">
                        <div
                            className="movieInfo__contents__img"
                            // 영화 이미지
                            style={{ backgroundImage: `url(${imgUrl})` }}
                        >
                        </div>
                        <div className="movieInfo__contents__col">
                            <div className="movieInfo__contents__titile">
                                {/* 영화 제목 */}
                                {title}
                            </div>
                            <div className="movieInfo__contents__info">
                                {/* 저자 및 출판사 정보 등 */}
                                <span className="bold">{description} </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div >
        )
};

export default MovieInfo2;
