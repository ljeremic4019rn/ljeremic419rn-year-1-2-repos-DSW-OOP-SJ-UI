
import React from "react";
import PropTypes from "prop-types";
import classNames from "classnames";
import { URLS } from "../routes";

function SidebarItem(props) {
    const style = classNames(
        "py-3 px-5", // spacing
        "text-white font-bold",
        "cursor-pointer",
        "hover:bg-gray-600", // hover
        "transition ease-in-out", // effects
    )

    return (
        <a className={style} href={props.href}>{props.text}</a>
    )
}

SidebarItem.propTypes = {
    text: PropTypes.string.isRequired,
    href: PropTypes.string.isRequired,
}

function Sidebar(props) {
    const style = classNames(
        "inset-y-0 left-0", //position
        "w-60 h-screen",
        "bg-gray-500",
    )

    return (
        <div className={style} >
            <div className="mt-8 flex flex-col">
                <SidebarItem text="Home" href={URLS.DASHBOARD.INDEX} />
                <SidebarItem text="Informacije" href={"/" + URLS.DASHBOARD.INFORMATION} />
                <SidebarItem text="Privatnost" href={"/" + URLS.DASHBOARD.PRIVACY} />
                <SidebarItem text="Lista zaposlenih" href={"/" + URLS.DASHBOARD.LIST.INDEX} />
            </div>
        </div>
    );
}

Sidebar.propTypes = {
}

export default Sidebar