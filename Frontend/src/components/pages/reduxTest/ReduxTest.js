import React from 'react';
import Subscribers from '../../Subscribers';
import Display from '../../Display';
import Views from '../../Views';
import styles from "./ReduxTest.module.css";

const ReduxTest = () => {
  return(
    <div className={styles.boxContainer}>
        <Subscribers/>
        <Views />
        <Display />
    </div>
  )
}

export default ReduxTest