/* eslint-disable */

import React from 'react';

// scss
import '../styles/sort-select.scss';

// 부트스트랩
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';

// 리덕스
import { actionCreators as moviesActions } from "../redux/modules/movies";
import { useDispatch } from "react-redux";

// 메인 페이지의 영화 목록 보기 선택 옵션
// 최신순, 좋아요순, 별점순
const SortSelect = () => {

  const dispatch = useDispatch();

  // 기본값은 최신순
  const [select, setSelect] = React.useState('createdAt');

  // 서버로부터 영화 목록 가져오기
  React.useEffect(() => {
    dispatch(moviesActions.movieListAPI(select));
  }, []);

  // 드롭박스가 바뀌면 그것에 맞추어 영화 목록 가져오기
  const sortSelection = (e) => {
    setSelect(e.target.value);
    dispatch(moviesActions.movieListAPI(e.target.value));
  }

  // 드롭박스
  return (
    <div className="sortselect">
      <Select
        labelId="demo-simple-select-label"
        id="demo-simple-select"
        onChange={sortSelection}
        value={select}
      >
        <MenuItem value="createdAt">최신순 보기</MenuItem>
        <MenuItem value="heart">좋아요순 보기</MenuItem>
        <MenuItem value="starRate">별점순 보기</MenuItem>
      </Select>
    </div>
  )
  
}

  

export default SortSelect;