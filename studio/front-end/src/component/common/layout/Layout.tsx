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
  min-width: 350px;
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
  > div:last-child {
    margin-top: auto;
  }
`;

const LayoutContent = styled.div`
  width: 100%;
  padding: 26px;
  @media (max-width: 600px) {
    padding: 10px;
  }
`

const Layout: React.FC = () => {
    // 첫 페이지 방문 안했을 시, 첫 페이지로
    const navigate = useNavigate();

    useEffect(() => {
        if (!GetCookie('visited')) navigate('/')
    }, [navigate])

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