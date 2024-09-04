import { themes } from 'mdx-deck'

const customTheme = {
  styles: {
    a: {
      color: 'red' // Using 'red' for this example
    }
  }
}

const theme = {
  ...themes.default,
  ...customTheme
}

console.log('Theme object:', theme)

export default theme
