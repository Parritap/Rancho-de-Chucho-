#!/bin/zsh

cd /Users/esteban/IdeaProjects/'POS Resturant App'/src/main/java/co/edu/uniquindio/pos_resturant_app/dto
# The string containing the folder names

string="admin cocinero ingrediente mesa mesero orden plato tipoPlato unidadMedida"
list=("${(@s/ /)string}")  # Split string by spaces into an array


# Iterate over the space-separated names in the string
for name in $list; do
  # Create the directory.  The -p option prevents errors if the
  # parent directories don't exist.  It also prevents errors if
  # the directory already exists.
  mkdir "$name"

  # Optional:  Check if the directory creation was successful.
  if [[ $? -eq 0 ]]; then
    echo "Created directory: $name"
  else
    echo "Failed to create directory: $name"
  fi
done

echo "Finished processing."#!/bin/zsh

                           cd /Users/esteban/IdeaProjects/'POS Resturant App'/src/main/java/co/edu/uniquindio/pos_resturant_app/dto
                           # The string containing the folder names

                           string="admin cocinero ingrediente mesa mesero orden plato tipoPlato unidadMedida"
                           list=("${(@s/ /)string}")  # Split string by spaces into an array


                           # Iterate over the space-separated names in the string
                           for name in $list; do
                             # Create the directory.  The -p option prevents errors if the
                             # parent directories don't exist.  It also prevents errors if
                             # the directory already exists.
                             mkdir "$name"

                             # Optional:  Check if the directory creation was successful.
                             if [[ $? -eq 0 ]]; then
                               echo "Created directory: $name"
                             else
                               echo "Failed to create directory: $name"
                             fi
                           done

                           echo "Finished processing."