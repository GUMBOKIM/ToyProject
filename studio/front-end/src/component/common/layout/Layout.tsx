import React from "react";
import {Outlet} from "react-router-dom";
import styled from "styled-components";
import GNB from "./GNB";

const LayoutContainer = styled.div`
  width: 100vw;
`

const LayoutHeader = styled.div`
  width: 100%;
  min-width: 350px;
  height: 80px;
  position: fixed;
  top: 0;
  z-index: 100;
  background-color: white;
`

const LayoutBody = styled.div`
  top: 120px;
  position: absolute;
  width: 100%;
  min-height: calc(100vh - 60px);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
`;

const LayoutContent = styled.div`
  width: 100%;
  padding: 0 26px;
  @media (max-width: 600px) {
    padding: 10px;
  }
`
const MainHeader = styled.div`
  position: absolute;
  top: 78px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: white;
`

const DivdeBar = styled.div`
  margin-bottom: 10px;
  width: 66px;
  height: 1px;
  background-color: black;
`

const MenuName = styled.div`
  font-size: large;
  font-weight: 500;
  margin-bottom: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  letter-spacing: .3rem;
`


const Layout: React.FC = () => {
    return (
        <LayoutContainer>
            <LayoutHeader>
                <GNB/>
                {/*TODO: 이거 메인 메뉴 쪽은 안나오게*/}
                <MainHeader>
                    <DivdeBar/>
                    <MenuName>Interview</MenuName>
                </MainHeader>
            </LayoutHeader>
            <LayoutBody>
                <LayoutContent>
                    <Outlet/>
                </LayoutContent>
            </LayoutBody>
        </LayoutContainer>
    )
}

export default Layout;