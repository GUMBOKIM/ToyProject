import styled from "styled-components";
import React, {useState} from "react";
import HamburgerIcon from "../../asset/icon/HamburgerIcon";
import {useNavigate} from "react-router-dom";

const GNBContainer = styled.div`
  position: relative;
  width: 100%;
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  z-index: 101;
  
  padding: 0 26px;
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
  top: 60px;
  width: 100%;
  display: flex;
  flex-direction: column;
  background-color: white;
  animation: moveDown 1s;
  z-index: 99;
  @keyframes moveDown {
    from {
      opacity: 0.5;
      transform: translate3d(0, -100%, 0);
    }
    to {
      opacity: 1;
      transform: translateZ(0);
    }
  }
  @media (min-width: 600px) {
    display: none;
  }
`

const GNBDropDownItem = styled.div`
  padding-left: 10px;
  height: 30px;
  font-size: medium;
  color: black;
  display: flex;
  align-items: center;

  :hover {
    font-weight: bold;
    cursor: pointer;
  }

  @media (min-width: 600px) {
    display: none;
  }
`

const GNB: React.FC = () => {
    const [isDDOpen, setIsDDOpen] = useState(false);
    const navigate = useNavigate();
    const link = (link: string) => {
        return () => {
            navigate(link);
            isDDOpen && setIsDDOpen(false);
        }
    }

    return (
        <>
            <GNBContainer>
                <GNBLeft>
                    <GNBMenuList>
                        <GNBMenuItem onClick={link('/board/viral')}>Viral</GNBMenuItem>
                        <GNBMenuItem onClick={link('/board/commerce')}>Commerce</GNBMenuItem>
                        <GNBMenuItem onClick={link('/board/photo')}>Photo</GNBMenuItem>
                    </GNBMenuList>
                    {/*모바일 부분*/}
                    <GNBHamburger onClick={() => setIsDDOpen(!isDDOpen)}>
                        <HamburgerIcon/>
                    </GNBHamburger>
                </GNBLeft>
                <GNBCenter>
                    <GNBLogo onClick={link('/main')}>ART STUDIO</GNBLogo>
                </GNBCenter>
                <GNBRight>
                    <GNBMenuList>
                        <GNBMenuItem onClick={link('/about')}>About</GNBMenuItem>
                        <GNBMenuItem onClick={link('/contact')}>Contact</GNBMenuItem>
                    </GNBMenuList>
                </GNBRight>
            </GNBContainer>
            {/*모바일 부분*/}
            {isDDOpen
                &&
                <GNBDropDown>
                    <GNBDropDownItem onClick={link('/board/viral')}>Viral</GNBDropDownItem>
                    <GNBDropDownItem onClick={link('/board/commerce')}>Commerce</GNBDropDownItem>
                    <GNBDropDownItem onClick={link('/board/photo')}>Photo</GNBDropDownItem>
                    <GNBDropDownItem onClick={link('/about')}>About</GNBDropDownItem>
                    <GNBDropDownItem onClick={link('/contact')}>Contact</GNBDropDownItem>
                </GNBDropDown>
            }
        </>
    )
}

export default GNB;