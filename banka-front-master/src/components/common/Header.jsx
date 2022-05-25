import React from "react";
import classNames from "classnames";

function Header(props) {
    const style = classNames(
        "absolute inset-x-0 top-0", //position
        "bg-gray-400", // background
        "text-2xl leading-9 text-white font-semibold", // font
        "py-4 px-5", // spacing
    )


    return (
        <div className={style}>
            <div className="flex justify-between">
                <div className="flex">
                    <div className="pr-12">
                        <svg width="46.5" height="46.5" viewBox="0 0 50 50" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M14.6667 30.1667V37.9167M25 30.1667V37.9167M35.3333 30.1667V37.9167M1.75 48.25H48.25M1.75 19.8333H48.25M1.75 12.0833L25 1.75L48.25 12.0833H1.75ZM4.33333 19.8333H45.6667V48.25H4.33333V19.8333Z" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                    </div>
                    <div className="mr-5">
                        POČETNA
                    </div>
                    <div className="mr-5">
                        SPISAK ZAPOSLENIH
                    </div>
                </div>
                <div className="underline">
                    markoMarkovic
                </div>

            </div>
        </div>
    );
}

export default Header
