import styled from "styled-components";
import React from "react";
import HamburgerIcon from "../../asset/icon/HamburgerIcon";

const GNBContainer = styled.div`
  position: relative;
  width: 100%;
  height: 60px;
  padding: 0 26px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  @media (max-width: 600px) {
    padding: 0 10px;
  }
`

const GNBLeft = styled.div``;

const GNBCenter = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateX(-50%) translateY(-50%);
`;

const GNBRight = styled.div``

const GNBLogo = styled.div`
  font-size: x-large;
  font-weight: bold;
  :hover {
    cursor: pointer;
  }
`

const GNBMenuList = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
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
  @media (max-width: 800px) {
    margin: 0 5px;
  }
  :hover {
    cursor: pointer;
  }
`;

const GNBHamburger = styled.div`
  height: 100%;
  width: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  @media (min-width: 600px) {  
    display: none;
  }
`

const GNBDropDown = styled.div`
  position: absolute;
  width: 100vh;
  top: 60px;
  
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-color: white;
  @media (min-width: 600px) {
    display: none;
  }
`

const GNBDropDownItem = styled.span`
  margin-left: 10px;
  height: 30px;
  font-size: medium;
  color: black;
  @media (min-width: 600px) {
    display: none;
  }
`

const GNB: React.FC = () => {
    return (
        <GNBContainer>
            <GNBLeft>
                <GNBMenuList>
                    <GNBMenuItem>Viral</GNBMenuItem>
                    <GNBMenuItem>Commerce</GNBMenuItem>
                    <GNBMenuItem>Photo</GNBMenuItem>
                </GNBMenuList>
                <GNBHamburger>
                    <HamburgerIcon/>
                    <GNBDropDown>
                        <GNBDropDownItem>Viral</GNBDropDownItem>
                        <GNBDropDownItem>Commerce</GNBDropDownItem>
                        <GNBDropDownItem>Photo</GNBDropDownItem>
                        <GNBDropDownItem>About</GNBDropDownItem>
                        <GNBDropDownItem>Contact</GNBDropDownItem>
                    </GNBDropDown>
                </GNBHamburger>
            </GNBLeft>
            <GNBCenter>
                <GNBLogo>ART STUDIO</GNBLogo>
            </GNBCenter>
            <GNBRight>
                <GNBMenuList>
                    <GNBMenuItem>About</GNBMenuItem>
                    <GNBMenuItem>Contact</GNBMenuItem>
                </GNBMenuList>
            </GNBRight>
        </GNBContainer>
    )
}

export default GNB;