import React from 'react'
import PropTypes from "prop-types";
import classNames from 'classnames';
import Button from './Button'
import ExitButton from './ExitButton';

function Modal(props) {
    const wrapperStyle = classNames(
        "fixed top-0 right-0 left-0 z-50 flex justify-center pt-8", // position
        "w-full h-full",
        "overflow-y-auto overflow-x-hidden", // overflow
        "bg-black/25",
        { "hidden": !props.visible },
    )

    const modalStyle = classNames(
        "w-full max-w-2xl h-full",
        "drop-shadow-2xl",
    )

    function renderHeader() {
        return (
            <div class="py-4 px-6 flex justify-between items-middle border-b bd-gray-300">
                <h3 class="text-2xl font-semibold text-gray-900 lg:text-2xl">
                    {props.title}
                </h3>
                <ExitButton onClick={handleClose} />
            </div>
        )
    }

    function handleClose() {
        props.onClose()
    }

    return (
        <div id={props.id} tabindex="-1" className={wrapperStyle}>
            <div class={modalStyle}>
                <div class="relative bg-white shadow">
                    {renderHeader()}
                    <div className='p-6'>
                        {props.children}
                        {props.cta &&
                            <div className="flex justify-end mt-5">
                                <Button label={"Click"} />
                            </div>
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}

Modal.propTypes = {
    visible: PropTypes.bool.isRequired,
    id: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    children: PropTypes.node,
    cta: PropTypes.func,
    onClose: PropTypes.func.isRequired,
}

export default Modal