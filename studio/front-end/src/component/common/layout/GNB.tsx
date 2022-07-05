import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import {MenuInfo} from "./GNBData";

import {
    GNBCenter,
    GNBContainer,
    GNBDropDown,
    GNBDropDownMainItem,
    GNBDropDownLeft,
    GNBLeft,
    GNBLogo,
    GNBMenuItem,
    GNBMenuList,
    GNBRight,
    GNBDropDownSubItem
} from "./Layout.style";

const subMenuList = (menuId:number) => {
    return MenuInfo.find(menu => menu.id === menuId)?.subMenu;
}

const GNB: React.FC = () => {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const [openMenu, setOpenMenu] = useState(0);
    const navigate = useNavigate();

    return (
        <>
            <GNBContainer>
                <GNBLeft>
                    <GNBMenuList>
                        <GNBMenuItem onMouseOver={() => setIsMenuOpen(true)}>
                            PortPolio
                        </GNBMenuItem>
                        <GNBMenuItem onClick={() => navigate("/archive")}>
                            Archive
                        </GNBMenuItem>
                        <GNBMenuItem onClick={() => navigate("/about")}>
                            About
                        </GNBMenuItem>
                        <GNBMenuItem onClick={() => navigate("/contact")}>
                            Contact
                        </GNBMenuItem>
                    </GNBMenuList>
                </GNBLeft>
                <GNBCenter>
                    <GNBLogo onClick={() => navigate("/main")}/>
                </GNBCenter>
                <GNBRight>
                </GNBRight>
            </GNBContainer>
            {
                isMenuOpen &&
                <GNBDropDown onMouseLeave={() => setIsMenuOpen(false)}>
                    <GNBDropDownLeft>
                    {MenuInfo.map(menu =>
                        <>
                        <GNBDropDownMainItem style={{fontSize : "medium"}}
                                             key={`menu-${menu.id}`}
                                             isSelected={menu.id === openMenu}
                                             onClick={() => setOpenMenu(menu.id)}>
                            {menu.menu}
                        </GNBDropDownMainItem>
                            { openMenu === menu.id &&
                                subMenuList(openMenu)?.map(subMenu =>
                                    <GNBDropDownSubItem key={`subMenu-${subMenu.id}`}
                                                         onClick={() => {
                                                             navigate(`/board/${subMenu.id}`)
                                                             setIsMenuOpen(false);
                                                         }}
                                    >
                                        • {subMenu.menu}
                                    </GNBDropDownSubItem>
                                )
                            }
                        </>
                    )}
                    </GNBDropDownLeft>
                </GNBDropDown>
            }
        </>
    )
}

export default GNB;