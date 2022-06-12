import React from "react";
import styled from "styled-components";
import {useNavigate} from "react-router-dom";

export interface CardItemProps {
    id: number;
    title: string;
    imgUrl: string;
    size: 'half' | 'full';
}

const CardItemWrapper = styled.div<{ size: 'half' | 'full' }>`
  position: relative;
  width: calc(${
          props => {
            if (props.size === 'half') {
              return '50% - 13px';
            } else {
              return '100%'
            }
          }
  });
  padding-bottom: calc(${
          props => {
            if (props.size === 'half') {
              return '50% - 13px';
            } else { 
              return '100% / 2'
            }
          }
  });
  margin-bottom: 26px;
  @media (max-width: 600px) {
    width: calc(${
            props => {
              if (props.size === 'half') {
                return '50% - 5px';
              } else {
                return '100%'
              }
            }
    });
    padding-bottom: calc(${
            props => {
              if (props.size === 'half') {
                return '50% - 5px';
              } else {
                return '100% / 2'
              }
            }
    });
    margin-bottom: 10px;
  }
  @media (max-width: 350px) {
    width: 100%;
    padding-bottom: calc(${
            props => {
              if (props.size === 'half') {
                return '100%';
              } else {
                return '50%'
              }
            }
    });
    margin-bottom: 10px;
  }
`

const CardItemImage = styled.div<{ imgUrl: string }>`
  width: 100%;
  height: 100%;
  position: absolute;
  background: url(${props => props.imgUrl});
  opacity: 0.2;
  background-size: cover;
  
  :hover {
    transition: opacity 1s;
    opacity: 1;
    cursor: pointer;
  }
`

const CardItemTitle = styled.div`
  position: absolute;
  text-align: center;
  font-size: x-large;
  width: 100%;
  top: 60%;
`

const CardItem: React.FC<{ card: CardItemProps }> = ({card}) => {
    const navigate = useNavigate();
    return (
        <CardItemWrapper size={card.size} onClick={() => navigate(`/post/${card.id}`)}>
            <CardItemImage imgUrl={card.imgUrl}>
                <CardItemTitle>{card.title}</CardItemTitle>
            </CardItemImage>

        </CardItemWrapper>
    );
}

export default CardItem;
