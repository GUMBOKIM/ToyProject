import React from "react";
import CardItemList from "../common/card/CardItemList";
import styled from "styled-components";

const Title = styled.div`
  font-weight: 700;
  font-size: large;
  line-height: 1.07;
  letter-spacing: .64px;
`

const Content = styled.div`
  font-size: medium;
  line-height: 1.25;
  letter-spacing: .73px;
`

const MainPage: React.FC = () => {
    return <>
        <CardItemList/>
        <div>Main Page</div>
        <Title>SPRING/SUMMER 2022</Title>
        <Content>
            A sense of calmness and a desire for comfort resonates in the Spring/Summer 2022 collection, best described as a toast to easy life and simple pleasures—a call to focus on what matters truly, explained by AERON’s Founder and Creative Director, Eszter Aron. </Content>
    </>
}

export default MainPage;