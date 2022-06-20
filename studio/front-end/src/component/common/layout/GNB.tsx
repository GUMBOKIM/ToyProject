import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import {MenuInfo} from "./GNBData";

import {GNBCenter, GNBContainer, GNBDropDown,
    GNBDropDownItem, GNBDropDownLeft, GNBDropDownRight, GNBLeft, GNBLogo, GNBMenuItem, GNBMenuList, GNBRight } from "./Layout.style";

const subMenuList = (menuId:number) => {
    return MenuInfo.find(menu => menu.id === menuId)?.subMenu;
}

const GNB: React.FC = () => {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const [openMenu, setOpenMenu] = useState(0);
    const [openSubMenu, setOpenSubMenu] = useState(0);
    const navigate = useNavigate();

    return (
        <>
            <GNBContainer>
                <GNBLeft>
                    <GNBMenuList>
                        <GNBMenuItem onMouseOver={() => setIsMenuOpen(true)}>
                            PortPolio
                        </GNBMenuItem>
                        <GNBMenuItem onClick={() => navigate("/sketch")}>
                            Sketch
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
                        <GNBDropDownItem key={`menu-${menu.id}`}
                                         isSelected={menu.id === openMenu}
                                         onClick={() => setOpenMenu(menu.id)}>
                            {menu.menu}
                        </GNBDropDownItem>
                    )}
                    </GNBDropDownLeft>
                    <GNBDropDownRight>
                        {
                            subMenuList(openMenu)?.map(subMenu =>
                                <GNBDropDownItem key={`subMenu-${subMenu.id}`}
                                                 isSelected={subMenu.id === openSubMenu}
                                                 onClick={() => {
                                                     setOpenSubMenu(subMenu.id);
                                                     navigate(`/board/${subMenu.id}`);
                                                     setIsMenuOpen(false);
                                                 }}
                                >
                                    {subMenu.menu}
                                </GNBDropDownItem>
                            )
                        }
                    </GNBDropDownRight>
                </GNBDropDown>
            }
        </>
    )
}

export default GNB;