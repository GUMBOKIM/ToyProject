import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import {CardItemProps, CardType} from "./CardItemData";
import {
    CardItemFullContainer,
    CardItemHalfContainer,
    CardItemImage, CardItemOpacity,
    CardItemText,
    CardItemTitle,
    CardVimeoItem
} from "./CardItem.style";

const CardItem: React.FC<{ card: CardItemProps }> = ({card}) => {
    const [isHover, setIsHover] = useState(false);
    const navigate = useNavigate();

    const linkPost = () => {
        if (card.type !== CardType.Text) navigate(`post/${card.id}`);
    }
    return (<>
            {
                card.size === 'full'
                &&
                <CardItemFullContainer onClick={linkPost}
                                       onMouseOver={() => setIsHover(true)}
                                       onMouseLeave={() => setIsHover(false)}>
                    {card.type === CardType.Image && <CardItemImage imgUrl={card.url!}/>}
                    {card.type === CardType.Video && <CardVimeoItem videoUrl={card.url!} isHover={isHover}/>}
                    {isHover && <CardItemTitle >{card.title}</CardItemTitle>}
                    <CardItemOpacity/>
                </CardItemFullContainer>
            }
            {
                card.size === 'half'
                &&
                <CardItemHalfContainer onClick={linkPost}
                                       onMouseOver={() => setIsHover(true)}
                                       onMouseLeave={() => setIsHover(false)}>
                    {card.type === CardType.Image && <CardItemImage imgUrl={card.url!}/>}
                    {card.type === CardType.Text && <CardItemText>{card.content}</CardItemText>}
                    {isHover && <CardItemTitle >{card.title}</CardItemTitle>}
                    <CardItemOpacity/>
                </CardItemHalfContainer>
            }
        </>
    );
}

export default CardItem;
