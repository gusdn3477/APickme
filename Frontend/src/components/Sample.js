import React, {useState} from 'react';

export default function Sample() {

    //let name = name === 'mike' ? 'jane' : 'mike';

    //let name = 'mike';
    const [name, setName] = useState('mike');
    function handleClick(){
        let newName = name === 'mike' ? 'jane' : 'mike';
        setName(newName);
    }

    function showText(event){
        console.log(event.target.value);
    }
    
    return(
        <div>
            <h1>Hello</h1>
            <h2>{name}</h2>
            <button onClick={handleClick}>Showname</button>
            <input type="text" onChange={showText}/>
            {/* <button onClick={showName}>ShowName</button>
            <button onClick={()=>{
                showAge(30);
            }}>
                ShowAge
            </button>
            <button onClick={()=>{
                //let gender = gender === 'woman' ? 'man' : 'woman';
                console.log('woman');
            }}>
                ShowGender
            </button>
            <input type="text" onChange={showText}/> */}
        </div>
    );
}