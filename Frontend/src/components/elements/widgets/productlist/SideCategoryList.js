import React, {useEffect, useState} from "react";

export default function SideCategoryList({item, setCategoryName}){

    const [chk , setChk] = useState(false);
    const handleCheck = (name) => {
        setCategoryName(name)
        setChk(!chk)
    }

    let process = require('../../../../db/myProcess.json');

    const [newData, setnewData] = useState([]);
    console.log(newData);
    
    useEffect(() => {
        fetch(`http://${process.IP}:${process.PORT}/product`)
        .then(res => {
            return res.json();
        })
        .then(data => {
            setnewData(data);
            console.log(data);
        })
        
    },[process.IP, process.PORT]);

    return(
        <li key ={item.id}>
            <div className="sidebar-widget-list-left">
                <button onClick = {() => handleCheck(item.name)}><span className= { chk ? 'mark' : 'checkmark' } ></span>{item.name}</button>
            </div>
        </li>
    );
}