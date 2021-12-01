import { connect } from 'react-redux'
import { addView } from '../redux/views/action'
import React, {useState} from 'react'

const Views = ({count, addView}) => {
    const [number, setNumber] = useState(1)
    return (
        <div>
            <p>조회수 : {count}</p>
            <input type="text" value={number} onChange={(e)=>setNumber(e.target.value)}/>
            <button onClick={()=> addView(number)}>입력</button>
        </div>
    );
}

// 호출
const mapStatetoProps = (state) => {
    return {
        count: state.views.count
    }
}

// addView 사용하기 위한 처리
const mapDispatchToProps = {
    // addView는 number를 받아서 addView(number) 함수를 호출한다[괄호안에는 인자값]
    addView : (number) => addView(number)
}

// mapStatetoProps를 통해 views의 count state를 connect

export default connect(mapStatetoProps, mapDispatchToProps)(Views)