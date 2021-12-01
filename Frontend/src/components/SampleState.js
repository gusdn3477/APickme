import React, {useState} from 'react';
import SampleProps from './SampleProps';

export default function SampleState() {

    // let name = "Mike";
    // function changeName() {
    //     name = name === "Mike" ? "Jane" : "Mike";
    //     console.log(name);
    //     document.getElementById("name").innerHTML = name;

    // }
    const [name, setName]=useState('Mike');

    function changeName(){
        let newName = name === "Mike" ? "Jane" : "Mike";
        setName(newName);
    }

    return (
        <div>
            <h1>State</h1>
            <h2 id="name">{name}</h2>
            <SampleProps name={name}/>
            <button onClick={changeName}>change</button>
        </div>
    );

}