Cell


| Responsibilities      | Collaborators    |
| :------------- | :----------: |
|  Knows State   | Model   |
| Knows Location   | Grid |
| Stores Next State   | ViewController |

Grid


| Responsibilities      | Collaborators    |
| :------------- | :----------: |
|  Stores Cells | Cell |
|   | Model |
|   | ViewController |

Model


| Responsibilities      | Collaborators    |
| :------------- | :----------: |
|  Handle Rule Logic | Cells   |
| Update Cells   | Grid |
| Store Possible States   | ViewController |

Theme


| Responsibilities      | Collaborators    |
| :------------- | :----------: |
|  Store Cell Colors | ViewController  |


View


| Responsibilities      | Collaborators    |
| :------------- | :----------: |
|  Fetch Cell States | Model  |
| Display Grind  | Grid |
| | Cell |
|   | Theme |

Controller


| Responsibilities      | Collaborators    |
| :------------- | :----------: |
|  Handle User Input | Model  |
| Manipulate Model Class | Grid |