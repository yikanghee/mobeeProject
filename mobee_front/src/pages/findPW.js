/* eslint-disable */

import React from "react";
import { withRouter } from "react-router";
import { Route, Link } from "react-router-dom";
import "../styles/signup.scss";

// 모듈
import { useDispatch } from "react-redux";
import { actionCreators as userActions } from "../redux/modules/user";


// 페이지 이동을 위한 history
import { history } from '../redux/configStore';

const findPW = (props) => {
    const dispatch = useDispatch();

    const [email, setEmail] = React.useState("");


    const findPW = () => {

        let emailRegExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;

        if (!emailRegExp.test(email)) {
            alert('이메일 형식을 확인해주세요.')
        }

        if (email === "") {
            window.alert('이메일이 공란입니다.');
            return;
        }

        console.log("email = " + email);

        dispatch(userActions.pwAPI(email));
    }

    return (
        <React.Fragment>
            <body className="login_body">

            <div className="header_box">
                <h1 className="ridi_logo"
                    onClick={() => {
                        history.push('/')
                    }}
                >MOBEE</h1>
            </div>
            <div className="Container">
                <div className="form_box">

                    <input
                        className="input_solo"
                        placeholder="이메일"
                        type="text"
                        onChange={(e) => {
                            setEmail(e.target.value);
                        }}
                    >
                    </input>
                    <button
                        className="login_button"
                        onClick={findPW}
                    >비밀번호 찾기</button>
                    <div className="button_group">
                        <button
                            className="signin_button"
                            onClick={history.goBack}
                        >뒤로가기
                        </button>
                    </div>
                </div>

            </div>
            </body>
        </React.Fragment>
    );
};

export default findPW;

