import React from 'react';
import GlobalStyles from "./style/GlobalStyles";
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import MainPage from "./component/pages/MainPage";
import BoardPage from "./component/pages/BoardPage";
import Layout from "./component/common/layout/Layout";
import PostPage from "./component/pages/PostPage";
import AboutPage from "./component/pages/AboutPage";
import ContactPage from "./component/pages/ContactPage";

function App() {
    return (
        <BrowserRouter>
            <GlobalStyles/>
            <Routes>
                <Route element={<Layout/>}>
                    <Route path='main' element={<MainPage/>}/>
                    <Route path='about' element={<AboutPage/>}/>
                    <Route path='contact' element={<ContactPage/>}/>
                    <Route path='archive' element={<ContactPage/>}/>
                    <Route path='sketch' element={<ContactPage/>}/>
                    <Route path='board/:boardId' element={<BoardPage/>}/>
                    <Route path='post/:postId' element={<PostPage/>}/>
                </Route>
                <Route path='*' element={<Navigate replace to={'/main'}/>}/>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
