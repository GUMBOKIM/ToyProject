import styled from "styled-components";

export const GNBContainer = styled.div`
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

export const GNBLeft = styled.div`
`;

export const GNBCenter = styled.div`
  position: absolute;
  top: calc(50%);
  left: 50%;
  transform: translateX(-50%) translateY(-50%);
`;

export const GNBRight = styled.div``

export const GNBLogo = styled.div`
  width: 100px;
  height: 50px;
  background: url('img/logo.png') no-repeat center;
  background-size: contain;
  z-index: 103;
  :hover {
    cursor: pointer;
  }
`

export const GNBMenuList = styled.div`
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

export const GNBMenuItem = styled.div`
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

export const GNBDropDown = styled.div`
  position: absolute;
  top: 80px;
  width: 100%;
  display: flex;
  background-color: white;
  z-index: 99;
  padding: 10px 26px;
`

export const GNBDropDownMainItem = styled.div<{isSelected?: boolean}>`
  height: 30px;
  font-size: medium;
  color: black;
  display: flex;
  align-items: center;
  :hover {
    font-weight: 500;
    cursor: pointer;
  }
  ${props => {
    if(props.isSelected)
        return `font-weight: 600;
              cursor: pointer;
              text-decoration: underline;
              `
}
}
}
`

export const GNBDropDownSubItem = styled.div`
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

export const GNBDropDownLeft = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
`

export const GNBDropDownRight = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`