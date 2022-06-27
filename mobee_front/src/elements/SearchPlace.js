import React, { useState } from "react";
import styled from 'styled-components';
import MapContainer from "./MapContainer";

import '../styles/map.scss';


const SearchPlace = () => {
    const [inputText, setInputText] = useState("");
    const [place, setPlace] = useState("");

    const onChange = (e) => {
        setInputText(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault(); //form의 onSubmit 함수에는 이걸 해야 아래 내용이 실행됨
        setPlace(inputText); //inputText를 place에 담음
        setInputText(""); //input창 다시 초기화
    };

    return (
        <>
        <form className="inputForm" onSubmit={handleSubmit}>
            <TextField placeholder="검색어를 입력해주세요" onChange={onChange} value={inputText} />
            &nbsp;<Button type="submit">
                    <span style={{ fontSize: "12px", fontWeight: "bold" }}>
                        검색
                    </span>
                </Button>
        </form>
        <MapContainer searchPlace={place} />
        </>
    );
};



const TextField = styled.input`
    width: 300px;
    height: 20px;
    border: none;
    border-bottom: 1px solid black;
`;

const Button = styled.button`
    width: 80px;
    height: 30px;
    border: none;
    border-radius: 20px;
    background-color: #78dfff;
    box-shadow: 2px 2px rgba(0, 0, 0, 0.35);
    cursor: pointer;
`;

export default SearchPlace;