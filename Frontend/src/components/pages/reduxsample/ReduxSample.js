
import React from 'react'
import Subscribers from '../../Subscribers'
import styles from './ReduxSample.module.css'
import Views from '../../Views'

const ReduxSample = () => {
    return (
        <div className={styles.reduxContainer}>
            <Subscribers />
            <Views />
        </div>
    );
}

export default ReduxSample