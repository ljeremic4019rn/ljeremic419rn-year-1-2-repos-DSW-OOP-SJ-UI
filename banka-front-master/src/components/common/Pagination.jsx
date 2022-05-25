import React, { useState, useEffect } from 'react'
import PropTypes from 'prop-types'
import classNames from 'classnames'


function PaginationButton(props) {
    const classes = classNames(
        'w-10 h-10', // spacing
        'bg-white', // background
        'border-y border-l last:border-r', // border
        'leading-none text-center select-none', // text
        'flex justify-center items-center', // alignment
        { 'font-bold': props.active },
        { 'cursor-pointer hover:bg-gray-100': props.onClick }, // interactive
        { 'opacity-60 text-gray-400 hover:bg-white cursor-not-allowed': props.disabled },
    )

    return (
        <div className={classes} onClick={props.onClick}>
            {props.label}
        </div>
    )
}

PaginationButton.propTypes = {
    label: PropTypes.any.isRequired,
    active: PropTypes.bool.isRequired,
    disabled: PropTypes.bool,
    onClick: PropTypes.func,
}

PaginationButton.defaultProps = {
    active: false,
    disabled: false,
}


function Pagination(props) {
    const [current, setCurrent] = useState(0)
    const [groupCount, setGroupCount] = useState(calculateGroupCount())

    function calculateGroupSlice() {
        const start = current * props.groupSize
        const end = (current >= groupCount) ? props.itemCount : (current + 1) * props.groupSize - 1
        return [start, end]
    }

    function calculateGroupCount() {
        if (props.itemCount === 0) return 1
        return Math.ceil(props.itemCount / props.groupSize)
    }

    function setActiveGroup(group) {
        if (group < 0) {
            return
        }
        if (group > groupCount - 1) {
            return
        }
        setCurrent(group)
    }

    function renderButtons() {
        const offset = 2
        let buttons = []

        // no breaking
        if (groupCount <= offset * 3 + 1) {
            for (let i = 0; i < groupCount; i++) {
                buttons.push(<PaginationButton label={i + 1} active={current === i} onClick={() => setActiveGroup(i)} />)
            }
            return buttons
        }

        // breaking in the middle
        if (current <= offset - 1 || current >= groupCount - offset) {
            for (let i = 0; i < offset + 1; i++) {
                buttons.push(<PaginationButton label={i + 1} active={current === i} onClick={() => setActiveGroup(i)} />)
            }
            buttons.push(<PaginationButton label="..." onClick={null} />)
            for (let i = groupCount - offset - 1; i < groupCount; i++) {
                buttons.push(<PaginationButton label={i + 1} active={current === i} onClick={() => setActiveGroup(i)} />)
            }
            return buttons
        }

        // left breaking transition
        if (current == offset) {
            for (let i = 0; i < offset + 2; i++) {
                buttons.push(<PaginationButton label={i + 1} active={current === i} onClick={() => setActiveGroup(i)} />)
            }

            buttons.push(<PaginationButton label="..." onClick={null} />)
            for (let i = groupCount - offset; i < groupCount; i++) {
                buttons.push(<PaginationButton label={i + 1} active={current === i} onClick={() => setActiveGroup(i)} />)
            }
            return buttons
        }

        // right breaking transition
        if (current == groupCount - offset - 1) {
            for (let i = 0; i < offset; i++) {
                buttons.push(<PaginationButton label={i + 1} active={current === i} onClick={() => setActiveGroup(i)} />)
            }

            buttons.push(<PaginationButton label="..." onClick={null} />)
            for (let i = groupCount - offset - 2; i < groupCount; i++) {
                buttons.push(<PaginationButton label={i + 1} active={current === i} onClick={() => setActiveGroup(i)} />)
            }
            return buttons
        }

        // breaking on the side
        buttons.push(<PaginationButton label={1} active={current === 0} onClick={() => setActiveGroup(0)} />)
            buttons.push(<PaginationButton label="..." onClick={null} />)
        for (let i = current - offset + 1; i < current + offset; i++) {
            buttons.push(<PaginationButton label={i + 1} active={current === i} onClick={() => setActiveGroup(i)} />)
        }
            buttons.push(<PaginationButton label="..." onClick={null} />)
        buttons.push(<PaginationButton label={groupCount} active={current === groupCount} onClick={() => setActiveGroup(groupCount - 1)} />)

        return buttons
    }

    useEffect(() => {
        setGroupCount(calculateGroupCount())
    }, [props.itemCount, props.groupSize])

    useEffect(() => {
        props.onChange(calculateGroupSlice())
    }, [current])

    return (
        <div className='flex'>
            <PaginationButton label='<' onClick={() => setActiveGroup(current - 1)} disabled={current === 0} />
            {renderButtons().map(i => i)}
            <PaginationButton label='>' onClick={() => setActiveGroup(current + 1)} disabled={current === groupCount - 1} />
        </div>
    )
}

Pagination.propTypes = {
    itemCount: PropTypes.number.isRequired,
    groupSize: PropTypes.number,
    onChange: PropTypes.func,
}

Pagination.defaultProps = {
    groupSize: 5,
}

export default Pagination