import React from 'react';

// export default function SampleProps({name}) {
//     return(
//         <div>
//             <h3>hello, {name}</h3>
//         </div>
//     );
// }

export default function SampleProps(props) {
    console.log(props);
    return(
        <div>
            <h3>hello, {props.name}</h3>
        </div>
    );
}