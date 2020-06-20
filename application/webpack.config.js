const purgecss = require('@fullhuman/postcss-purgecss')
const Dotenv = require('dotenv-webpack');

module.exports = {
  module: {
    rules: [
      {
        test: /\.scss$/,
        loader: 'postcss-loader',
        options: {
          ident: 'postcss',
          syntax: 'postcss-scss',
          plugins: () => [
            require('postcss-import'),
            require('tailwindcss'),
            purgecss({
              content: [
                './src/**/*.html', './src/**/*.ts'
              ],
              defaultExtractor: content => content.match(/[\w-/:]+(?<!:)/g) || []
            }),
            require('autoprefixer'),
          ]
        }
      }
    ]
  },
  plugins: [
    new Dotenv({
      path: "../.env"
    })
  ]
};
