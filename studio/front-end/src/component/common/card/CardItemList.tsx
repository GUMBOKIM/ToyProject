import React from "react";
import CardItem, {CardItemProps} from "./CardItem";
import styled from "styled-components";

const CardItemListContainer = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
`

// const CardItemList: React.FC<CardItemProps[]> = (cardList) => {
const CardItemList: React.FC = () => {
    const cardList: CardItemProps[] = [
        {
            id: 1,
            title: 'Hello Mother',
            imgUrl: 'https://picsum.photos/500',
            size: 'half'
        }, {
            id: 2,
            title: 'Hello MotherFather',
            imgUrl: 'https://picsum.photos/500',
            size: 'half'
        }, {
            id: 3,
            title: 'Hello MotherFather',
            imgUrl: 'https://picsum.photos/500/1000',
            size: 'full'
        },
        {
            id: 5,
            title: 'Hello MotherFather',
            imgUrl: 'https://picsum.photos/500/1000',
            size: 'full'
        }, {
            id: 6,
            title: 'Hello MotherFather',
            imgUrl: 'https://picsum.photos/500',
            size: 'half'
        },
        {
            id: 4,
            title: 'Hello Mother',
            imgUrl: 'https://picsum.photos/500',
            size: 'half'
        },
    ]
    return (
        <CardItemListContainer>
            {cardList.map(card => <CardItem key={card.id} card={card}/>)}
        </CardItemListContainer>
    );
}

export default CardItemList;
