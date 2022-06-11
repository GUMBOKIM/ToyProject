import React, {useEffect} from "react";
import {Outlet, useNavigate} from "react-router-dom";
import {GetCookie} from "../../../util/Cookie";
import styled from "styled-components";
import GNB from "./GNB";
import FNB from "./FNB";

const LayoutContainer = styled.div`
  width: 100vw;
`

const LayoutHeader = styled.div`
  width: 100%;
  height: 60px;
  position: fixed;
  top: 0;
  z-index: 100;
  background-color: white;
`

const LayoutBody = styled.div`
  top: 60px;
  position: absolute;
  
  width: 100%;
  min-height: calc(100vh - 60px);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;

  div:last-child {
    margin-top: auto;
  }
`;

const LayoutContent = styled.div`
  width: 100%;
  padding: 0 26px 0 26px;
`

const Layout: React.FC = () => {
    // 첫 페이지 방문 안했을 시, 첫 페이지로
    const navigate = useNavigate();
    useEffect(() => {
        if (!GetCookie('visited')) navigate('/')
    }, [])

    return (
        <LayoutContainer>
            <LayoutHeader>
                <GNB/>
            </LayoutHeader>
            <LayoutBody>
                <LayoutContent>
                    <Outlet/>
                </LayoutContent>
                <FNB/>
            </LayoutBody>
        </LayoutContainer>
    )
}

export default Layout;