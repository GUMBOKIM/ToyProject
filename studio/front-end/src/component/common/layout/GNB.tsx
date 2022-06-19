import styled from "styled-components";
import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import {MenuInfo} from "./GNBData";

const GNBContainer = styled.div`
  position: relative;
  width: 100%;
  height: 80px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  z-index: 101;

  padding: 0 26px;
  @media (max-width: 600px) {
    padding: 0 10px;
  }
`

const GNBLeft = styled.div`
`;

const GNBCenter = styled.div`
  position: absolute;
  top: calc(50%);
  left: 50%;
  transform: translateX(-50%) translateY(-50%);
`;

const GNBRight = styled.div``

const GNBLogo = styled.div`
  width: 100px;
  height: 50px;
  background: url('img/logo.png') no-repeat center;
  background-size: contain;
  z-index: 103;

  :hover {
    cursor: pointer;
  }
`

const GNBMenuList = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  padding-top: 25px;

  div:first-of-type {
    margin-left: 0;
  }

  div:last-of-type {
    margin-right: 0;
  }

  @media (max-width: 600px) {
    display: none;
  }
`;

const GNBMenuItem = styled.div`
  height: 100%;
  margin: 0 10px;
  font-size: small;
  letter-spacing: 0.06rem;
  
  @media (max-width: 800px) {
    margin: 0 5px;
  }

  :hover {
    cursor: pointer;
  }
`;

const GNBDropDown = styled.div`
  position: absolute;
  top: 80px;
  width: 100%;
  display: flex;
  flex-direction: column;
  background-color: white;
  z-index: 99;
  padding: 10px 26px;
`

const GNBDropDownItem = styled.div`
  height: 30px;
  font-size: small;
  color: black;
  display: flex;
  align-items: center;

  :hover {
    font-weight: 500;
    cursor: pointer;
  }
`

const GNB: React.FC = () => {
    const [selectMenu, setSelectMenu] = useState(0);
    const navigate = useNavigate();

    return (
        <>
            <GNBContainer>
                <GNBLeft>
                    <GNBMenuList>
                        {
                            MenuInfo.map(item =>
                                <GNBMenuItem key={item.id}
                                             onMouseOver={() => setSelectMenu(item.id)}
                                >
                                    {item.menu}
                                </GNBMenuItem>)
                        }
                        <GNBMenuItem onMouseOver={() => setSelectMenu(0)}
                                     onClick={() => navigate("/sketch")}
                        >Sketch</GNBMenuItem>
                        <GNBMenuItem onMouseOver={() => setSelectMenu(0)}
                                     onClick={() => navigate("/archive")}
                        >Archive</GNBMenuItem>
                        <GNBMenuItem onMouseOver={() => setSelectMenu(0)}
                                     onClick={() => navigate("/about")}
                        >About</GNBMenuItem>
                        <GNBMenuItem onMouseOver={() => setSelectMenu(0)}
                                     onClick={() => navigate("/contact")}
                        >Contact</GNBMenuItem>
                    </GNBMenuList>
                </GNBLeft>
                <GNBCenter>
                    <GNBLogo onClick={() => navigate("/main")}/>
                </GNBCenter>
                <GNBRight>
                    <GNBMenuList>
                    </GNBMenuList>
                </GNBRight>
            </GNBContainer>
            {
                selectMenu !== 0 &&
                <GNBDropDown onMouseLeave={() => setSelectMenu(0)}>
                    {
                        MenuInfo.find(menu => menu.id === selectMenu)?.subMenu?.map(item =>
                            <GNBDropDownItem key={item.id} onClick={() => navigate(`/board/${item.id}`)}>
                                {item.menu}
                            </GNBDropDownItem>)
                    }
                </GNBDropDown>
            }
        </>
    )
}

export default GNB;