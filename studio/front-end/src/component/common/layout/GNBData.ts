export interface MenuProps {
    id: number;
    order: number;
    menu: string;
    subMenu?: MenuProps[];
}

export const MenuInfo: MenuProps[] = [
    {
        id: 1,
        order: 1,
        menu: 'Viral',
        subMenu: [
            {
                id: 2,
                order: 1,
                menu: 'producing'
            }, {
                id: 3,
                order: 2,
                menu: 'gaffer'
            }
        ]
    },
    {
        id: 4,
        order: 2,
        menu: 'Beauty',
        subMenu: [
            {
                id: 5,
                order: 1,
                menu: 'producing'
            }, {
                id: 6,
                order: 2,
                menu: 'edit'
            }
        ]
    },
    {
        id: 7,
        order: 3,
        menu: 'Fashion',
        subMenu: [
            {
                id: 8,
                order: 1,
                menu: 'producing'
            }, {
                id: 9,
                order: 2,
                menu: 'gaffer'
            },
            {
                id: 10,
                order: 3,
                menu: 'edit'
            }
        ]
    },
    {
        id: 11,
        order: 4,
        menu: 'Youtube',
        subMenu: [
            {
                id: 12,
                order: 1,
                menu: 'producing'
            }, {
                id: 13,
                order: 2,
                menu: 'gaffer'
            }
        ]
    },
    {
        id: 14,
        order: 5,
        menu: 'Interview',
        subMenu: [
            {
                id: 15,
                order: 1,
                menu: 'producing'
            }, {
                id: 16,
                order: 2,
                menu: 'gaffer'
            },
            {
                id: 17,
                order: 3,
                menu: 'edit'
            }
        ]
    },
    {
        id: 18,
        order: 6,
        menu: 'Sketch'
    },
]