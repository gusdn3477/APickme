import React from 'react'
import styles from './Subscribers.module.css'
import { connect } from 'react-redux'
import { addSubscriber, removeSubscriber } from '../redux/subscribers/action';
//import viewsReducer from './redux_backup/views/reducer';
//import { addSubscriber } from '../redux/subscribers/action'


const Subscribers = ({count, addSubscriber, removeSubscriber}) => {
    return (
        <div className={styles.items}>
            <h2>구독자수 : {count} </h2>
            <button onClick={()=>addSubscriber()}>구독하기!</button> 
            <button onClick={()=>removeSubscriber()}>구독취소!</button> 
        </div>
    );
}

const mapStatetoProps = ( state ) => {

    return {
        count: state.subscribers.count
    }
}

const mapDispatchToProps = {
    addSubscriber,
    removeSubscriber:removeSubscriber
}



export default connect( mapStatetoProps, mapDispatchToProps )( Subscribers )