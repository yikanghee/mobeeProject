/* eslint-disable */

import React from 'react';

// 컴포넌트
import Header from '../components/Header';
import MapContainer from '../elements/MapContainer';
import SearchPlace from '../elements/SearchPlace';
import Footer from '../components/Footer';

import '../styles/map.scss';
import Cards2 from "../components/Cards2";

// 메인 페이지 컴포넌트
const KakaoMap = (props) => {

  return (
    <div className='KakaoMap'>
      <Header history={props.history} />
        <Cards2 history={props.history}/>
      <SearchPlace />
      <MapContainer />
      <Footer />
    </div>
    
  )
};

export default KakaoMap;
