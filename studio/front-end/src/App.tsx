import React from 'react';
import GlobalStyles from "./style/GlobalStyles";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import HomePage from "./component/pages/HomePage";
import MainPage from "./component/pages/MainPage";
import BoardPage from "./component/pages/BoardPage";
import Layout from "./component/common/layout/Layout";

function App() {
    return (
        <BrowserRouter>
            <GlobalStyles/>
            <Routes>
                <Route path='/' element={<HomePage/>}/>
                <Route  element={<Layout />}>
                    <Route path='main' element={<MainPage/>}/>
                    <Route path='board/:boardName' element={<BoardPage/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
