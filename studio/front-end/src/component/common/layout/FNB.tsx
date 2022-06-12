import styled from "styled-components";
import React from "react";

const FNBContainer = styled.div`
  width: 100%;
  height: 100px;
  
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  background-color: #fbf3e2;
`

const FNB: React.FC = () => {
    return (
        <FNBContainer>
            <div>여기에 이거저거 정보 들어갑니다.</div>
        </FNBContainer>
    )
}

export default FNB;