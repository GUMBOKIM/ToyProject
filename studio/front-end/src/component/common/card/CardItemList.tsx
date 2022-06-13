import React from "react";
import CardItem from "./CardItem";
import styled from "styled-components";
import {CardDataList} from "./CardItemData";

const CardItemListContainer = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
`

// const CardItemList: React.FC<CardItemProps[]> = (cardList) => {
const CardItemList: React.FC = () => {

    return (
        <CardItemListContainer>
            {CardDataList.map(card => <CardItem key={card.id} card={card}/>)}
        </CardItemListContainer>
    );
}

export default CardItemList;
