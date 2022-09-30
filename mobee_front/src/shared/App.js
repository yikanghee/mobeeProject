import React from 'react';

// 라우터
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import { ConnectedRouter } from 'connected-react-router';
import { history } from '../redux/configStore';

// 페이지
import Main from '../pages/Main';
import Login from '../pages/Login';
import Signup from '../pages/Signup';
import Detail from '../pages/Detail';
import KakaoMap from '../pages/KakaoMap';
import Detail2 from "../components/Detail2";
import findPW from "../pages/findPW";
import findID from "../pages/findID";

// Not Found
import NotFound from '../pages/NotFound';

// 부트스트랩
import 'bootstrap/dist/css/bootstrap.min.css';

const App = (props) => {
    return (
        <BrowserRouter>
            <React.Fragment>
                <ConnectedRouter history={history}>
                    <Switch>
                        {/* <Route path="/" */}
                        <Route path='/' exact component={Main}/>
                        <Route path='/login' exact component={Login}/>
                        <Route path='/signup' exact component={Signup}/>
                        <Route path='/kakaoMap' exact component={KakaoMap}/>
                        <Route path='/detail/:id' exact component={Detail}/>
                        <Route path='/detail2/:id' exact component={Detail2}/>
                        <Route path='/findPW' exact component={findPW}/>
                        <Route path='/findId' exact component={findID}/>
                        {/* Not Found */}
                        <Route component={NotFound}/>
                    </Switch>
                </ConnectedRouter>
            </React.Fragment>
        </BrowserRouter>
    );
};

export default App;