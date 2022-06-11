import styled from "styled-components";
import React from "react";

const FNBContainer = styled.div`
  width: 100%;
  
  display: flex;
  align-items: center;
  flex-direction: column;
  background-color: #fbf3e2;

`

const FNB: React.FC = () => {
    return (
        <FNBContainer>
            <div>Test</div>
            <div>Test</div>
            <div>Test</div>
            <div>Test</div>
        </FNBContainer>
    )
}

export default FNB;