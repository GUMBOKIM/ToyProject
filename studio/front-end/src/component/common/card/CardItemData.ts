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
        url: 'https://picsum.photos/500',
        size: 'half'
    }, {
        id: 2,
        type: CardType.Image,
        title: 'Hello MotherFather',
        url: 'https://picsum.photos/500',
        size: 'half'
    }, {
        id: 3,
        type: CardType.Video,
        title: 'Hello MotherFather',
        url: 'https://player.vimeo.com/video/719721362',
        size: 'full'
    },
    {
        id: 5,
        type: CardType.Video,
        title: 'Hello MotherFather',
        url: 'https://player.vimeo.com/video/719721362',
        size: 'full'
    }, {
        id: 6,
        type: CardType.Image,
        title: 'Hello MotherFather',
        url: 'https://picsum.photos/500',
        size: 'half'
    },
    {
        id: 4,
        type: CardType.Image,
        title: 'Hello Mother',
        url: 'https://picsum.photos/500',
        size: 'half'
    },
]