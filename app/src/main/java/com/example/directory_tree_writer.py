import os

def write_directory_tree_to_file(path, output_file):
    """Write the directory tree of `path` to `output_file`."""
    with open(output_file, 'w') as file:
        for root, dirs, files in os.walk(path):
            # Calculate the level of indentation based on the root directory
            level = root.replace(path, '').count(os.sep)
            indent = ' ' * 4 * level
            file.write(f'{indent}{os.path.basename(root)}/\n')
            
            # Write files in the current directory
            for f in files:
                file.write(f'{indent}    {f}\n')

def main():
    folder_path = input("Enter the path of the folder: ")
    output_file = 'directory_tree.txt'
    
    if not os.path.isdir(folder_path):
        print(f"The path '{folder_path}' is not a valid directory.")
        return
    
    write_directory_tree_to_file(folder_path, output_file)
    print(f"Directory tree has been written to {output_file}")

if __name__ == "__main__":
    main()
