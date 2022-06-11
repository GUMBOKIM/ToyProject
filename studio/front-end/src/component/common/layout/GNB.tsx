import styled from "styled-components";
import React from "react";

const GNBContainer = styled.div`
  width: 100%;
  height: 60px;
  padding: 0 26px;

  display: flex;
  justify-content: space-between;
  align-items: center;
  
  
  position: relative;
`

const GNBLeft = styled.div``;

const GNBMenuList = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const GNBMenuItem = styled.div`
  height: 100%;
  margin-right: 25px;
  font-size: small;
  
  @media (max-width: 800px) {
    margin-right: 10px;
  }

  :hover {
    cursor: pointer;
  }
`;

const GNBCenter = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateX(-50%) translateY(-50%);
`;

const GNBLogo = styled.div`

  font-size: x-large;
  font-weight: bold;

  :hover {
    cursor: pointer;
  }
`

const GNBRight = styled.div`
    width: 100px;
  height: 100%;
  background-color: red;
`


const GNB: React.FC = () => {
    return (
        <GNBContainer>
            <GNBLeft>
                <GNBMenuList>
                    <GNBMenuItem>Main</GNBMenuItem>
                    <GNBMenuItem>Viral</GNBMenuItem>
                    <GNBMenuItem>Commerce</GNBMenuItem>
                    <GNBMenuItem>Photo</GNBMenuItem>
                    <GNBMenuItem>About</GNBMenuItem>
                    <GNBMenuItem>Contact</GNBMenuItem>
                </GNBMenuList>
            </GNBLeft>
            <GNBCenter>
                <GNBLogo>ART STUDIO</GNBLogo>
            </GNBCenter>
            <GNBRight/>
        </GNBContainer>
    )
}

export default GNB;