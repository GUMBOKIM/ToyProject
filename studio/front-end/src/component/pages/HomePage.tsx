import React, {useEffect} from "react";
import styled from "styled-components";
import {Link} from "react-router-dom";
import {SetCookie} from "../../util/Cookie";

const HomeContainer = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: lightgrey;
  background-size: cover;
`


const EnterButton = styled.div`
  width: 200px;
  height: 80px;

  display: flex;
  justify-content: center;
  align-items: center;
  
  font-size: 1rem;

  :hover {
    cursor: pointer
  }
`

const HomePage: React.FC = () => {
    useEffect(() => {
        SetCookie('visited', true);
    }, [])

    return (
        <HomeContainer>
            <Link to={"/main"}>
                <EnterButton>Enter</EnterButton>
            </Link>
        </HomeContainer>
    )
}

export default HomePage;