import styled from "styled-components";
import React from "react";


// wPh -> 가로세로 비율
export const CardItemFullContainer = styled.div<{wPh? : number}>`
  overflow: hidden;
  position: relative;
  width: 100%;
  padding-bottom: 100% / ${props => props.wPh ? props.wPh : '16 / 9'};
  margin-bottom: 26px;
  @media (max-width: 1080px) {
    margin-bottom: 10px;
  }
`

export const CardItemHalfContainer = styled.div`
  overflow: hidden;
  position: relative;
  width: calc((100% - 13px) / 2);
  padding-bottom: calc((100% - 13px) / 2);
  margin-bottom: 26px;
  @media (max-width: 1080px) {
    width: calc((100% - 5px) / 2);
    padding-bottom:  calc((100% - 5px) / 2);
    margin-bottom: 10px;
  }
`

export const CardItemImage = styled.div<{ imgUrl: string }>`
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

export const CardItemText = styled.div`
  width: 100%;
  height: 100%;
  position: absolute;
  
  display: flex;
  justify-content: center;
  align-items: center;
`

const CardItemVimeoWrapper = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
`

export const CardVimeoItem: React.FC<{ videoUrl: string, isHover: boolean }> = ({videoUrl, isHover}) => {
    return (
        <CardItemVimeoWrapper>
            <iframe title="vimeo-player" src={videoUrl + '&background=1' + '&autoplay=' + (isHover ? 1 : 0)}
                    width={'100%'} height={'100%'}/>
        </CardItemVimeoWrapper>
    );
}

export const CardItemTitle = styled.div`
  position: absolute;
  text-align: center;
  font-size: x-large;
  height: 60px;
  width: 100%;
  bottom: 0;
  background-color: grey;

  animation: moveUp 0.3s linear;

  @keyframes moveUp {
    from {
      display: none;
      transform: translate3d(0, 100%, 0);
    }
    to {
      transform: translateZ(0);
    }
  }
`