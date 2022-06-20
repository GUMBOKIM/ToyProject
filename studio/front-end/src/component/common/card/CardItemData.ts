export const CardType = {
    Video : 'video',
    Image : 'image',
    Text : 'text'
}

export interface CardItemProps {
    id: number;
    type: string;
    title: string;
    url?: string;
    content?: string;
    size: 'half' | 'full';
}

export const CardDataList: CardItemProps[] = [
    {
        id: 1,
        type: CardType.Image,
        title: 'Hello Mother',
        url: 'img/test/1.jpg',
        size: 'half'
    }, {
        id: 2,
        type: CardType.Image,
        title: 'Hello MotherFather',
        url: 'img/test/2.jpg',
        size: 'half'
    }, {
        id: 3,
        type: CardType.Video,
        title: 'Hello MotherFather',
        url: 'https://player.vimeo.com/video/719722319?h=22897be7c3',
        size: 'full'
    },
    {
        id: 5,
        type: CardType.Video,
        title: 'Hello MotherFather',
        url: 'https://player.vimeo.com/video/719721362?h=30ae47c2e2',
        size: 'full'
    }, {
        id: 6,
        type: CardType.Image,
        title: 'Hello MotherFather',
        url: 'img/test/3.jpg',
        size: 'half'
    },
    {
        id: 4,
        type: CardType.Image,
        title: 'Hello Mother',
        url: 'img/test/4.jpg',
        size: 'half'
    },
    {
        id: 10,
        type: CardType.Image,
        title: 'Hello Mother',
        url: 'img/test/5.jpg',
        size: 'half'
    },
]